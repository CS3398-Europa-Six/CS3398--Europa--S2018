import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DicerollComponent } from './diceroll.component';

describe('DicerollComponent', () => {
  let component: DicerollComponent;
  let fixture: ComponentFixture<DicerollComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DicerollComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DicerollComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
