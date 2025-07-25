import { Directive, HostListener, ViewContainerRef, Output, ElementRef, EventEmitter, OnChanges, Input, Renderer2 } from '@angular/core';

@Directive({
  selector: '[appMousePosition]'
})
export class MousePositionDirective implements OnChanges {
  @Input('appMousePosition') component: string;
  @Output() positions = new EventEmitter<number[]>();
  constructor(private el: ElementRef, private renderer: Renderer2) { }
  @HostListener('click', ['$event', '$event.target'])
  onMousemove(event: MouseEvent, target) {
    if (target.localName === this.component) {
      const positions = [event.clientX, event.clientY];
      this.positions.emit(positions);
    }
  }
  ngOnChanges() {
  }
}
