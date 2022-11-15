import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {CaffResponse} from "../models/caff-response";
import {CommentResponse} from "../models/comment-response";

const API_URL = 'http://localhost:8080/api/';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient) { }

  getComments(caffId:string): Observable<CommentResponse[]> {
    return this.http.get<CaffResponse[]>(API_URL + 'caff/'+caffId+"/comment");
  }

}
