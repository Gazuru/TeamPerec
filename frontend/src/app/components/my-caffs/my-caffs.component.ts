import {Component, OnInit} from '@angular/core';
import {CaffService} from "../../services/caff.service";
import {CaffResponse} from "../../models/caff-response";
import {TokenStorageService} from "../../services/token-storage.service";

@Component({
  selector: 'app-my-caffs',
  templateUrl: './my-caffs.component.html',
  styleUrls: ['./my-caffs.component.css']
})
export class MyCaffsComponent implements OnInit {
  caffs: CaffResponse[] = [];

  constructor(private caffService: CaffService, private tokenStorageService: TokenStorageService) {

  }

  ngOnInit(): void {
    this.getMyCaffsList();
  }

  getMyCaffsList() {
    this.caffService.getMyCaffsList().subscribe(
      data => {
        console.log(data);
        this.caffs = data;
      }
    );
  }

}
