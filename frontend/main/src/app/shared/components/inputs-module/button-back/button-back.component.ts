import { Component, OnInit, ChangeDetectionStrategy, Inject } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ButtonView } from '../../../../core/class/button-componet/button';

@Component({
  selector: 'app-button-back',
  template: `
        <mat-action-row>
        <button class="btnRegresar" class="btn btn-danger" *appHideShow='btnBack.default'
         appBackButton [translate]='"button.back"'></button>
        <button class="btnRegresar" class="btn btn-danger" *appHideShow='!btnBack.default'
        [translate]='"button.back"' (click)='go()'></button>
        </mat-action-row>`
  ,
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ButtonBackComponent extends ButtonView implements OnInit {
  constructor(private _router: Router, private _activatedRoute: ActivatedRoute) {
    super(_router, _activatedRoute);
  }

  ngOnInit() {
  }
}
