import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AssociationFormService } from './association-form.service';
import { AssociationService } from '../service/association.service';
import { IAssociation } from '../association.model';

import { AssociationUpdateComponent } from './association-update.component';

describe('Association Management Update Component', () => {
  let comp: AssociationUpdateComponent;
  let fixture: ComponentFixture<AssociationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let associationFormService: AssociationFormService;
  let associationService: AssociationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AssociationUpdateComponent],
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
      .overrideTemplate(AssociationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AssociationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    associationFormService = TestBed.inject(AssociationFormService);
    associationService = TestBed.inject(AssociationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const association: IAssociation = { id: 456 };

      activatedRoute.data = of({ association });
      comp.ngOnInit();

      expect(comp.association).toEqual(association);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssociation>>();
      const association = { id: 123 };
      jest.spyOn(associationFormService, 'getAssociation').mockReturnValue(association);
      jest.spyOn(associationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ association });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: association }));
      saveSubject.complete();

      // THEN
      expect(associationFormService.getAssociation).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(associationService.update).toHaveBeenCalledWith(expect.objectContaining(association));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssociation>>();
      const association = { id: 123 };
      jest.spyOn(associationFormService, 'getAssociation').mockReturnValue({ id: null });
      jest.spyOn(associationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ association: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: association }));
      saveSubject.complete();

      // THEN
      expect(associationFormService.getAssociation).toHaveBeenCalled();
      expect(associationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssociation>>();
      const association = { id: 123 };
      jest.spyOn(associationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ association });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(associationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
