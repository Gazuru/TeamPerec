import {Component, OnInit} from '@angular/core';
import {CaffService} from "../../services/caff.service";
import {CaffResponse} from "../../models/caff-response";
import {FormControl, FormGroup} from "@angular/forms";
import {EMPTY, Observable} from "rxjs";

@Component({
  selector: 'app-caffs',
  templateUrl: './caffs.component.html',
  styleUrls: ['./caffs.component.css']
})
export class CaffsComponent implements OnInit {
  caffForm = new FormGroup({
    name: new FormControl(),
    uploaderName: new FormControl()
  });

  caffs: Observable<CaffResponse[]> = EMPTY;

  constructor(private caffService: CaffService) {
  }

  ngOnInit(): void {
    this.getCaffsList();
  }

  searchCaffs() {
    this.caffs = this.caffService.getCaffsListSearch(this.caffForm.get('uploaderName')?.value, this.caffForm.get('name')?.value)
  }

  getCaffsList() {
    this.caffs = this.caffService.getCaffsList();
  }
}
