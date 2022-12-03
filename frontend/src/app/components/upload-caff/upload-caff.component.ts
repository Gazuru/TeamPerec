import {Component, OnInit} from '@angular/core';
import {CaffService} from "../../services/caff.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-upload-caff',
  templateUrl: './upload-caff.component.html',
  styleUrls: ['./upload-caff.component.css']
})
export class UploadCaffComponent implements OnInit {
  form: any;


  myForm = new FormGroup({
    name: new FormControl('', [Validators.required, Validators.minLength(3)]),
    description: new FormControl('', [Validators.required, Validators.minLength(10)]),
    file: new FormControl('', [Validators.required, Validators.pattern("^.*\\.(caff|CAFF|Caff)$")])
  });

  constructor(private caffService: CaffService) {
  }

  ngOnInit(): void {
  }

  get f() {
    return this.myForm.controls;
  }

  uploadFile(fileInput: any) {
    let files: File[] = fileInput.files;
    if (files.length < 1) {
      return;
    }
    let file = files[0];
    this.caffService.testUpload(file, this.myForm);
  }

}
