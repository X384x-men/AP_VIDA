import { Directive, Input, ElementRef, OnInit, OnChanges, Renderer2, SimpleChanges } from '@angular/core';
@Directive({
  selector: '[appDisableInput]'
})
export class DisableInputDirective implements OnInit, OnChanges {
  @Input('appDisableInput') disable: boolean;
  constructor(private el: ElementRef, private renderer: Renderer2) {
  }
  ngOnInit() {
  }
  ngOnChanges(changes: SimpleChanges) {
    if (this.disable !== undefined && this.disable !== null) {
      this.renderer.setProperty(this.el.nativeElement, 'disabled', this.disable);
    }
  }
}
