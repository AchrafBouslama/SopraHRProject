import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListPlaceparkingComponent } from './list-placeparking.component';

describe('ListPlaceparkingComponent', () => {
  let component: ListPlaceparkingComponent;
  let fixture: ComponentFixture<ListPlaceparkingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListPlaceparkingComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListPlaceparkingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
