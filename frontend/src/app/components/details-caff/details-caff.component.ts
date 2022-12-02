import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {CaffService} from "../../services/caff.service";
import {CaffResponse} from "../../models/caff-response";
import {CommentService} from "../../services/comment.service";
import {CommentResponse} from "../../models/comment-response";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'app-details-caff',
  templateUrl: './details-caff.component.html',
  styleUrls: ['./details-caff.component.css']
})
export class DetailsCaffComponent implements OnInit {
  caff: CaffResponse;
  comments: CommentResponse[];
  id: string = "";
  previewGifImage: any;

  myForm = new FormGroup({
    commentText: new FormControl('', [Validators.required, Validators.minLength(1)])
  });


  private sub: any;

  constructor(private route: ActivatedRoute, private caffService: CaffService, private commentService: CommentService, private sanitizer: DomSanitizer) {
    this.caff = {
      comments: undefined,
      description: "",
      id: undefined,
      imagePreviewBase64: undefined,
      name: "",
      uploader: undefined,
      uploaderName: ""
    };
    this.comments = [];
  }

  ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      this.id = params['id'];
    });
    this.getCaff();
  }

  get f() {
    return this.myForm.controls;
  }

  getCaff() {
    this.caffService.getCaff(this.id).subscribe(
      data => {
        this.caff = data;
      }, null, () => {
        this.comments = this.caff.comments;
        this.previewGifImage = this.sanitizer.bypassSecurityTrustResourceUrl(`data:image/gif;base64, ${this.caff.imagePreviewBase64}`);
      }
    );
  }

  downloadCAFF() {
    this.caffService.downloadFile(this.caff.id);
  }

  addComment() {
    this.commentService.postComment(this.myForm, this.caff.id);
    window.location.reload();
  }

}
