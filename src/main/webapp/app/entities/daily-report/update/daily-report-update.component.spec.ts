import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DailyReportFormService } from './daily-report-form.service';
import { DailyReportService } from '../service/daily-report.service';
import { IDailyReport } from '../daily-report.model';

import { DailyReportUpdateComponent } from './daily-report-update.component';

describe('DailyReport Management Update Component', () => {
  let comp: DailyReportUpdateComponent;
  let fixture: ComponentFixture<DailyReportUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let dailyReportFormService: DailyReportFormService;
  let dailyReportService: DailyReportService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DailyReportUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(DailyReportUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DailyReportUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    dailyReportFormService = TestBed.inject(DailyReportFormService);
    dailyReportService = TestBed.inject(DailyReportService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const dailyReport: IDailyReport = { id: 456 };

      activatedRoute.data = of({ dailyReport });
      comp.ngOnInit();

      expect(comp.dailyReport).toEqual(dailyReport);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDailyReport>>();
      const dailyReport = { id: 123 };
      jest.spyOn(dailyReportFormService, 'getDailyReport').mockReturnValue(dailyReport);
      jest.spyOn(dailyReportService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dailyReport });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dailyReport }));
      saveSubject.complete();

      // THEN
      expect(dailyReportFormService.getDailyReport).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(dailyReportService.update).toHaveBeenCalledWith(expect.objectContaining(dailyReport));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDailyReport>>();
      const dailyReport = { id: 123 };
      jest.spyOn(dailyReportFormService, 'getDailyReport').mockReturnValue({ id: null });
      jest.spyOn(dailyReportService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dailyReport: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dailyReport }));
      saveSubject.complete();

      // THEN
      expect(dailyReportFormService.getDailyReport).toHaveBeenCalled();
      expect(dailyReportService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDailyReport>>();
      const dailyReport = { id: 123 };
      jest.spyOn(dailyReportService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dailyReport });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(dailyReportService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
