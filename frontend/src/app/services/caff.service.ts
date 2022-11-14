import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {FormGroup} from "@angular/forms";

const API_URL = 'http://localhost:8080/api/caff/';

@Injectable({
  providedIn: 'root'
})
export class CaffService {

  constructor(private http: HttpClient) { }

  getCaffsList(): Observable<any> {
    return this.http.get(API_URL + 'list');
  }

  uploadCaff(myForm:FormGroup){
      const formData = new FormData();
      formData.append('file', myForm.get('fileSource')?.value);
      formData.append('name', myForm.get('name')?.value);
      formData.append('description', myForm.get('description')?.value);

      this.http.post(API_URL+"upload", formData)
        .subscribe(res => {
          console.log(res);
          alert('Uploaded Successfully.');
        })
  }

}
