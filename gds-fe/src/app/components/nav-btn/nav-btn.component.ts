import { Component, Input } from '@angular/core';
import { faImage } from '@fortawesome/free-regular-svg-icons';

@Component({
  selector: 'app-nav-btn',
  templateUrl: './nav-btn.component.html',
  styleUrls: ['./nav-btn.component.css']
})
export class NavBtnComponent {
  @Input() count = '';
  faImage = faImage;
}
