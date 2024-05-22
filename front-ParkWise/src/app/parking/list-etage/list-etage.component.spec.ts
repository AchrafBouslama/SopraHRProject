import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListEtageComponent } from './list-etage.component';

describe('ListEtageComponent', () => {
  let component: ListEtageComponent;
  let fixture: ComponentFixture<ListEtageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListEtageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListEtageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
