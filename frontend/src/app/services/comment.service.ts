import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CommentResponse} from "../models/comment-response";
import { FormGroup } from '@angular/forms';
import {CommentRequest} from "../models/comment-request";
import {Router} from "@angular/router";

const API_URL = 'http://localhost:8080/api/';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient,private router:Router) { }

  postComment(form: FormGroup,caffId:string) {
    const request: CommentRequest = {
      commentText: form.get('commentText')?.value
    };

    this.http.post<CommentResponse>(API_URL + "caff/"+caffId+"/comment", request)
      .subscribe();
  }

  deleteComment(commentId:string){
    this.http.delete(API_URL + "comment/" + commentId)
      .subscribe();
  }
}
