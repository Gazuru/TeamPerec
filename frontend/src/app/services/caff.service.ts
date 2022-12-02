import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, ReplaySubject} from "rxjs";
import {FormGroup} from "@angular/forms";
import {CaffResponse} from "../models/caff-response";
import {TokenStorageService} from "./token-storage.service";
import {CaffRequest} from "../models/caff-request";

const API_URL = 'http://localhost:8080/api/caff/';

@Injectable({
  providedIn: 'root'
})
export class CaffService {

  constructor(private http: HttpClient, private tokenStorageService: TokenStorageService) {
  }

  getCaffsList(): Observable<CaffResponse[]> {
    return this.http.get<CaffResponse[]>(API_URL + 'list');
  }

  getMyCaffsList(): Observable<CaffResponse[]> {
    const userId = this.tokenStorageService.getUser().id;
    return this.http.get<CaffResponse[]>(API_URL + 'list');
  }

  getCaff(id: string): Observable<CaffResponse> {
    return this.http.get<CaffResponse>(API_URL + id);
  }

  uploadCaff(file: File, myForm: FormGroup) {
    const formData = new FormData();
    //formData.append('file', myForm.get('fileSource')?.value);
    formData.append('name', myForm.get('name')?.value);
    formData.append('description', myForm.get('description')?.value,);

    let base64Output: string;
    this.convertFile(file).subscribe((base64) => {
      base64Output = base64;
      formData.append('file', base64Output);
      console.log(JSON.stringify(formData.get("file")));
      let headers = new HttpHeaders({
        'Content-Type': 'multipart/form-data'});
      let options = { headers: headers };
      this.http.post<CaffResponse>(API_URL + "upload", formData,options)
        .subscribe(res => {
          console.log(res);
          alert('Uploaded Successfully.');
        });
    }, null, () => {


    });
  }

  convertFile(file: File): Observable<string> {
    const result = new ReplaySubject<string>(1);
    const reader = new FileReader();
    reader.readAsBinaryString(file);
    reader.onload = (event) => {
      if (event.target && event.target.result) {
        result.next(btoa(event.target.result.toString()));
      }
    };
    return result;
  }

  public uploadFile(file: File, form: FormGroup): Observable<CaffResponse> {
    let formData = new FormData();
    formData.append("name", form.get('name')?.value);
    formData.append("description", form.get('description')?.value);
    //formData.append('file', this.convertFile(file), file.name);


    let base64Output: string;
    this.convertFile(file).subscribe((base64) => {
      base64Output = base64;
      formData.append('file', base64Output);
      let payload: CaffRequest = {
        name: "",
        description: "",
        image: ""
      };
      let request = this.http
        .post<CaffResponse>(
          API_URL + "upload",
          payload
        ).subscribe(null, (error) => {
          "asdasdasd" + console.log(error)
        });
      console.log(request)
      return request;
    }, (error) => {
      console.log(error);
    }, () => {

    });

    return new Observable<CaffResponse>();


  }

}
