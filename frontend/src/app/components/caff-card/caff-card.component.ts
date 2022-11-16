import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-caff-card',
  templateUrl: './caff-card.component.html',
  styleUrls: ['./caff-card.component.css']
})
export class CaffCardComponent implements OnInit {
  @Input() caff: any;


  constructor() {
  }

  ngOnInit(): void {

  }

}
