import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {CaffService} from "../../services/caff.service";
import {CaffResponse} from "../../models/caff-response";
import {CommentService} from "../../services/comment.service";
import {CommentResponse} from "../../models/comment-response";

@Component({
  selector: 'app-details-caff',
  templateUrl: './details-caff.component.html',
  styleUrls: ['./details-caff.component.css']
})
export class DetailsCaffComponent implements OnInit {
  caff: CaffResponse;
  comments: CommentResponse[];
  id: string = "";

  form: any = {
    comment: null
  };

  private sub: any;

  constructor(private route: ActivatedRoute, private caffService: CaffService, private commentService: CommentService) {
    this.caff = {
      comments: undefined, description: "", id: undefined, image: undefined, name: "", uploader: undefined
    };
    this.comments=[];
  }

  ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      this.id = params['id'];
    });
    this.getCaff();
    this.getComments();
  }

  getCaff() {
    this.caffService.getCaff(this.id).subscribe(
      data => {
        console.log(data);
        this.caff = data;
      }
    );
  }

  getComments() {
    this.commentService.getComments(this.id).subscribe(
      data => {
        console.log(data);
        this.comments = data;
      }
    );
  }

  downloadCAFF() {
  }

  onSubmit() {

  }
}
