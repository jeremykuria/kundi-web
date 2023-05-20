import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DailyReportDetailComponent } from './daily-report-detail.component';

describe('DailyReport Management Detail Component', () => {
  let comp: DailyReportDetailComponent;
  let fixture: ComponentFixture<DailyReportDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DailyReportDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ dailyReport: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DailyReportDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DailyReportDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load dailyReport on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.dailyReport).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
