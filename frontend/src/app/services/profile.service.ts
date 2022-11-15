import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {ProfileResponse} from "../models/profile-response";
import {HttpClient} from "@angular/common/http";

const API_URL = 'http://localhost:8080/api/user/';


@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private http: HttpClient) { }

  getProfile(id:string): Observable<any> {
    return this.http.get<any>(API_URL + id);
  }
}
