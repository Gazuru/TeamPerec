import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {JwtResponse} from "../models/jwt-response";
import {MessageResponse} from "../models/message-response";

const AUTH_API = 'http://localhost:8080/api/auth/';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*'})
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {
  }

  login(username: string, password: string): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(AUTH_API + 'signin', {
      username,
      password
    }, httpOptions);
  }

  register(username: string, email: string, password: string): Observable<MessageResponse> {
    return this.http.post<MessageResponse>(AUTH_API + 'signup', {
      username,
      email,
      password
    }, httpOptions);
  }
}
