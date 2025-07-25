import {
  Component, OnInit, Input, Type, OnChanges, Output, EventEmitter,
  ChangeDetectionStrategy, ChangeDetectorRef, AfterContentChecked
} from '@angular/core';
import { LoaderComponent } from 'src/app/core/class/loader-component/loader-component';
import { Router, ActivatedRoute } from '@angular/router';
import { LoaderComponentService } from 'src/app/core/services/loader-componet/loader-component.service';
import { LoaderComponet } from '../../../../core/interface/loader-component/loader-componet';

/**
 * REVISAR BUG YA QUE ONCHANGES NO ESTA CAMBIANDO EL VALOR ACTUAL POR EL DE ENTRADA
 */
@Component({
  selector: 'app-loader-component-tamplate',
  template: '<ng-template appAddComponent></ng-template>',
  changeDetection: ChangeDetectionStrategy.OnPush

})
export class LoaderComponentTamplateComponent extends LoaderComponent implements OnInit, AfterContentChecked, OnChanges {
  /**
   * COMPONENT
   */
  @Input() type: Type<any>;
  /**
   * DATA OF COMPONENT
   */
  @Input() data: any;
  /**
   * COMPONENT
   */
  @Input() parent: any;
  /**
   * INDEX OF CURRENT COMPONENT
   */
  @Input() createdComponent: LoaderComponet;

  @Output() createdComponentChange = new EventEmitter<LoaderComponet>();
  /**
   * Detect if the page was reloaded
   */
  @Input() reload: boolean;
  /**
   * Emit value if the page was reloaded
   */
  @Output() reloadChange = new EventEmitter<boolean>();

  @Output() dataEmmit = new EventEmitter<any>();
  constructor(private route: Router, private activated: ActivatedRoute,
    private loaderComponentService: LoaderComponentService, private cdr: ChangeDetectorRef) {
    super(route, activated, loaderComponentService);
  }

  ngOnInit() {
    if ((this.type && this.parent) !== undefined && (this.type != null && this.parent != null)) {
      const data = this.load(this.type, this.parent, this.data);
      this.createdComponentChange.emit({
        compInteraction: data.compInteraction,
        data: data.data,
        directive: data.directive,
        index: data.index,
        parent: data.parent,
        selfRef: data.selfRef,
        value: data.value
      });
    }
  }
  ngOnChanges() {
    if (this.reload) {
      this.changeView();
    }
  }
  ngAfterContentChecked(): void {
    this.changeView();
  }
  private changeView() {
    if ((this.type && this.parent) !== undefined && (this.type != null && this.parent != null)
      && (this.reload !== undefined && this.reload != null && this.reload)) {
      this.reloadChange.emit(null);
      const data = this.removeComponent(this.type, this.parent, this.data, this.createdComponent.index);
      this.send(data);
      this.createdComponentChange.emit({
        compInteraction: data.compInteraction,
        data: data.data,
        directive: data.directive,
        index: data.index,
        parent: data.parent,
        selfRef: data.selfRef,
        value: data.value
      });
    }
  }
  private send(component: LoaderComponet) {
    if (component && component.selfRef.value) {
      component.selfRef.value.subscribe(data => {
        this.dataEmmit.emit(data);
      });
    }

  }
}
