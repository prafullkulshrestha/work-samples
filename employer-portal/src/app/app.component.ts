import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'employer-portal';
  public drawerOpen = false;

  public setDrawerOpen(isOpen): void {
  		this.drawerOpen = isOpen;
  	}
}
