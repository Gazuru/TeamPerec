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

  descriptionMinLength: number = 10;
  nameMinLength: number = 3;

  myForm = new FormGroup({
    name: new FormControl('', [Validators.required, Validators.minLength(this.nameMinLength)]),
    description: new FormControl('', [Validators.minLength(this.descriptionMinLength)]),
    file: new FormControl('', [Validators.required, Validators.pattern("^.*\\.(caff|CAFF|Caff)$")]),
    fileSource: new FormControl('', [Validators.required])
  });

  constructor(private caffService: CaffService) {
  }

  ngOnInit(): void {
  }

  get f() {
    return this.myForm.controls;
  }

  /*onFileChange(event: any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.myForm.patchValue({
        fileSource: file
      });
    }
  }*/

  submit() {
    //this.caffService.uploadCaff(this.myForm);
  }

  public uploadResult?: any;

  async uploadFile(fileInput: any) {
    let files: File[] = fileInput.files;
    if (files.length < 1) {
      return;
    }

    let file = files[0];
    try {

      this.caffService.uploadCaff(file,this.myForm);

    } catch (error) {

      console.warn("File upload failed.");
      console.error(error);

    }
  }

}
