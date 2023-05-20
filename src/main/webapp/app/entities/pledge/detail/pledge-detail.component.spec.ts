import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PledgeDetailComponent } from './pledge-detail.component';

describe('Pledge Management Detail Component', () => {
  let comp: PledgeDetailComponent;
  let fixture: ComponentFixture<PledgeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PledgeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ pledge: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PledgeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PledgeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load pledge on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.pledge).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
