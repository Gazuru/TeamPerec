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
    name: "",
    uploaderName: ""
  };

  caffs: CaffResponse[] = [];

  constructor(private caffService: CaffService) {
  }

  ngOnInit(): void {
    this.getCaffsList();
  }

  searchCaffs() {
    this.caffService.getCaffsListSearch(this.form.uploaderName,this.form.name).subscribe(
      data => {
        this.caffs = data;
      }
    )
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
