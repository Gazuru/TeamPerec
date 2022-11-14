import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {FormGroup} from "@angular/forms";
import {Caff} from "../models/caff";

const API_URL = 'http://localhost:8080/api/caff/';

@Injectable({
  providedIn: 'root'
})
export class CaffService {

  constructor(private http: HttpClient) {
  }

  getCaffsList(): Observable<Caff[]> {
    return this.http.get<Caff[]>(API_URL + 'list');
  }

  uploadCaff(myForm: FormGroup) {
    const formData = new FormData();
    formData.append('file', myForm.get('fileSource')?.value);
    formData.append('name', myForm.get('name')?.value);
    formData.append('description', myForm.get('description')?.value);

    this.http.post<Caff>(API_URL + "upload", formData)
      .subscribe(res => {
        console.log(res);
        alert('Uploaded Successfully.');
      })
  }

}
