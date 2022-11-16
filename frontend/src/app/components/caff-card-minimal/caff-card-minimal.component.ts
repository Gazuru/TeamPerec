import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-caff-card-minimal',
  templateUrl: './caff-card-minimal.component.html',
  styleUrls: ['./caff-card-minimal.component.css']
})
export class CaffCardMinimalComponent implements OnInit {
  @Input() caff: any;

  constructor() { }

  ngOnInit(): void {
  }

}
