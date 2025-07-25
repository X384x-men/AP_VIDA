import { Component, OnInit, ChangeDetectionStrategy, Inject } from '@angular/core';
import { SubResourceService } from '../../../../core/services/service-crud-operations/sub-resource.service';
import { ButtonView } from '../../../../core/class/button-componet/button';

@Component({
  selector: 'app-button-create',
  template: `
        <mat-action-row>
        <button class="btnRegresar" class="btn btn-success"
        [translate]='"button.save"' (click)='create()'></button>
        </mat-action-row>`,
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ButtonCreateComponent extends ButtonView implements OnInit {

  constructor(@Inject('ServiceResource') private _subResourceService: SubResourceService<any>) {
    super(null, null, _subResourceService);
  }

  ngOnInit() {
  }


}
