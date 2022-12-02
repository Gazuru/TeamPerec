import {Component, Input, OnInit} from '@angular/core';
import {TokenStorageService} from "../../services/token-storage.service";
import {CommentService} from "../../services/comment.service";

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent implements OnInit {
  @Input() comment:any;
  showDeleteButton=false;

  constructor(private tokenStorageService: TokenStorageService,private commentService:CommentService) { }

  ngOnInit(): void {
    this.showDeleteButton=this.tokenStorageService.getUser().roles.includes("ROLE_ADMIN");
  }

  deleteComment() {
    this.commentService.deleteComment(this.comment.id);
    window.location.reload();
  }
}
