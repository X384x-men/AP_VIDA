import { Component, OnInit } from '@angular/core';
import { GlobalVariable } from 'src/app/core/static/variables/url/URLImages';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit {

  apvidaBackground  = GlobalVariable.BACKGROUND_IMG_APVIDA;
  constructor() { }

  ngOnInit() {
  }

}
