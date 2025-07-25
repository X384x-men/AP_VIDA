import { Directive, OnChanges, OnInit, Input, ElementRef, Renderer2, SimpleChanges } from '@angular/core';

@Directive({
  selector: '[appEnableDisableCheck]'
})
export class EnableDisableCheckDirective implements OnInit, OnChanges {
  @Input('appEnableDisableCheck') disable: boolean;
  constructor(private el: ElementRef, private renderer: Renderer2) {
  }
  ngOnInit() {
  }
  ngOnChanges(changes: SimpleChanges) {
    if (this.disable !== undefined && this.disable !== null) {
      this.renderer.setProperty(this.el.nativeElement, 'disabled', this.disable);
      this.renderer.setProperty(this.el.nativeElement, 'checked', this.disable);
    }
  }
}
