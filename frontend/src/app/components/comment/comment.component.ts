import { Component, OnInit } from '@angular/core';
import {TokenStorageService} from "../../services/token-storage.service";

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent implements OnInit {
  showDeleteButton=false;

  constructor(private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    this.showDeleteButton=this.tokenStorageService.getUser().roles.includes("ROLE_ADMIN");
  }

  deleteComment() {

  }
}
