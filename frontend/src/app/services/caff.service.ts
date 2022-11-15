import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {FormGroup} from "@angular/forms";
import {CaffResponse} from "../models/caff-response";
import {TokenStorageService} from "./token-storage.service";

const API_URL = 'http://localhost:8080/api/caff/';

@Injectable({
  providedIn: 'root'
})
export class CaffService {

  constructor(private http: HttpClient,private tokenStorageService: TokenStorageService) {
  }

  getCaffsList(): Observable<CaffResponse[]> {
    return this.http.get<CaffResponse[]>(API_URL + 'list');
  }

  getMyCaffsList(): Observable<CaffResponse[]> {
    const userId=this.tokenStorageService.getUser().id;
    return this.http.get<CaffResponse[]>(API_URL + 'list');
  }

  getCaff(id:string): Observable<CaffResponse> {
    return this.http.get<CaffResponse>(API_URL + id);
  }

  uploadCaff(myForm: FormGroup) {
    const formData = new FormData();
    formData.append('file', myForm.get('fileSource')?.value);
    formData.append('name', myForm.get('name')?.value);
    formData.append('description', myForm.get('description')?.value);

    this.http.post<CaffResponse>(API_URL + "upload", formData)
      .subscribe(res => {
        console.log(res);
        alert('Uploaded Successfully.');
      })
  }

}
