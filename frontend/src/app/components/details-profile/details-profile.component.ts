import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {CaffService} from "../../services/caff.service";

@Component({
  selector: 'app-details-profile',
  templateUrl: './details-profile.component.html',
  styleUrls: ['./details-profile.component.css']
})
export class DetailsProfileComponent implements OnInit {
  caff:any;
  id: string="";

  private sub: any;
  constructor(private route: ActivatedRoute,private caffService:CaffService) {
  }

  ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      this.id = params['id'];
    });
    this.getCaff();
  }

  getCaff(){
    this.caffService.getCaff(this.id).subscribe(
      data => {
        console.log(data);
        this.caff = data;
      }
    );
  }

}
