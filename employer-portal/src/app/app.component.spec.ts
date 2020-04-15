import { TestBed, async, ComponentFixture } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import { EpSharedModule } from './shared/shared.module';
import { By } from '@angular/platform-browser';
import { MatToolbar, MatToolbarRow } from '../../node_modules/@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('AppComponent', () => {
  let fixture: ComponentFixture<AppComponent>;
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [ RouterTestingModule, EpSharedModule, BrowserAnimationsModule ],
      declarations: [
        AppComponent
      ],
    }).compileComponents();
  }));
  beforeEach(() => {
    fixture = TestBed.createComponent(AppComponent);
  });
  it('should create the app', async(() => {
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
  it('should have a span element', async(() => {
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('span')).toBeTruthy();
  }));
  it('should have mat-tool bar tag', async(() => {
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('mat-toolbar')).toBeTruthy();
  }));
 
  it('should have desired text contents for the first anchor tag', async(() => {
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    expect((compiled.querySelectorAll('a')[0]).textContent).toEqual('About');
  }));

  it('should contain required texts', async(() => {
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.textContent).toContain('Enroll Employee');
    expect(compiled.textContent).toContain('Employee List');
    expect(compiled.textContent).toContain('Logout');
  }));
});
