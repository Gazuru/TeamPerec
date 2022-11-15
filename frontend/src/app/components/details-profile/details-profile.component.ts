import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {CaffService} from "../../services/caff.service";
import {ProfileService} from "../../services/profile.service";

@Component({
  selector: 'app-details-profile',
  templateUrl: './details-profile.component.html',
  styleUrls: ['./details-profile.component.css']
})
export class DetailsProfileComponent implements OnInit {
  profile:any;
  id: string="";

  private sub: any;
  constructor(private route: ActivatedRoute,private profileService:ProfileService) {
  }

  ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      this.id = params['id'];
    });
    this.getProfile();
  }

  getProfile(){
    this.profileService.getProfile(this.id).subscribe(
      data => {
        console.log(data);
        this.profile = data;
      }
    );
  }

}
