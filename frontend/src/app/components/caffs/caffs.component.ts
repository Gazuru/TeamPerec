import {Component, OnInit} from '@angular/core';
import {CaffService} from "../../services/caff.service";
import {CaffResponse} from "../../models/caff-response";

@Component({
  selector: 'app-caffs',
  templateUrl: './caffs.component.html',
  styleUrls: ['./caffs.component.css']
})
export class CaffsComponent implements OnInit {
  form: any = {
    name: null,
    uploader: null
  };

  caffs: CaffResponse[] = [];

  constructor(private caffService: CaffService) {
  }

  ngOnInit(): void {
    this.getCaffsList();
  }

  onSubmit() {
    return false;
  }

  getCaffsList() {
    this.caffService.getCaffsList().subscribe(
      data => {
        console.log(data);
        this.caffs = data;
      }
    );
  }
}
