import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AssocMemberFormService } from './assoc-member-form.service';
import { AssocMemberService } from '../service/assoc-member.service';
import { IAssocMember } from '../assoc-member.model';
import { IPledge } from 'app/entities/pledge/pledge.model';
import { PledgeService } from 'app/entities/pledge/service/pledge.service';
import { IAssociation } from 'app/entities/association/association.model';
import { AssociationService } from 'app/entities/association/service/association.service';

import { AssocMemberUpdateComponent } from './assoc-member-update.component';

describe('AssocMember Management Update Component', () => {
  let comp: AssocMemberUpdateComponent;
  let fixture: ComponentFixture<AssocMemberUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let assocMemberFormService: AssocMemberFormService;
  let assocMemberService: AssocMemberService;
  let pledgeService: PledgeService;
  let associationService: AssociationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AssocMemberUpdateComponent],
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
      .overrideTemplate(AssocMemberUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AssocMemberUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    assocMemberFormService = TestBed.inject(AssocMemberFormService);
    assocMemberService = TestBed.inject(AssocMemberService);
    pledgeService = TestBed.inject(PledgeService);
    associationService = TestBed.inject(AssociationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Pledge query and add missing value', () => {
      const assocMember: IAssocMember = { id: 456 };
      const pledge: IPledge = { id: 69546 };
      assocMember.pledge = pledge;

      const pledgeCollection: IPledge[] = [{ id: 66913 }];
      jest.spyOn(pledgeService, 'query').mockReturnValue(of(new HttpResponse({ body: pledgeCollection })));
      const additionalPledges = [pledge];
      const expectedCollection: IPledge[] = [...additionalPledges, ...pledgeCollection];
      jest.spyOn(pledgeService, 'addPledgeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ assocMember });
      comp.ngOnInit();

      expect(pledgeService.query).toHaveBeenCalled();
      expect(pledgeService.addPledgeToCollectionIfMissing).toHaveBeenCalledWith(
        pledgeCollection,
        ...additionalPledges.map(expect.objectContaining)
      );
      expect(comp.pledgesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Association query and add missing value', () => {
      const assocMember: IAssocMember = { id: 456 };
      const associations: IAssociation[] = [{ id: 50997 }];
      assocMember.associations = associations;

      const associationCollection: IAssociation[] = [{ id: 91628 }];
      jest.spyOn(associationService, 'query').mockReturnValue(of(new HttpResponse({ body: associationCollection })));
      const additionalAssociations = [...associations];
      const expectedCollection: IAssociation[] = [...additionalAssociations, ...associationCollection];
      jest.spyOn(associationService, 'addAssociationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ assocMember });
      comp.ngOnInit();

      expect(associationService.query).toHaveBeenCalled();
      expect(associationService.addAssociationToCollectionIfMissing).toHaveBeenCalledWith(
        associationCollection,
        ...additionalAssociations.map(expect.objectContaining)
      );
      expect(comp.associationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const assocMember: IAssocMember = { id: 456 };
      const pledge: IPledge = { id: 94484 };
      assocMember.pledge = pledge;
      const association: IAssociation = { id: 52717 };
      assocMember.associations = [association];

      activatedRoute.data = of({ assocMember });
      comp.ngOnInit();

      expect(comp.pledgesSharedCollection).toContain(pledge);
      expect(comp.associationsSharedCollection).toContain(association);
      expect(comp.assocMember).toEqual(assocMember);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssocMember>>();
      const assocMember = { id: 123 };
      jest.spyOn(assocMemberFormService, 'getAssocMember').mockReturnValue(assocMember);
      jest.spyOn(assocMemberService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assocMember });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: assocMember }));
      saveSubject.complete();

      // THEN
      expect(assocMemberFormService.getAssocMember).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(assocMemberService.update).toHaveBeenCalledWith(expect.objectContaining(assocMember));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssocMember>>();
      const assocMember = { id: 123 };
      jest.spyOn(assocMemberFormService, 'getAssocMember').mockReturnValue({ id: null });
      jest.spyOn(assocMemberService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assocMember: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: assocMember }));
      saveSubject.complete();

      // THEN
      expect(assocMemberFormService.getAssocMember).toHaveBeenCalled();
      expect(assocMemberService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssocMember>>();
      const assocMember = { id: 123 };
      jest.spyOn(assocMemberService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assocMember });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(assocMemberService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePledge', () => {
      it('Should forward to pledgeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(pledgeService, 'comparePledge');
        comp.comparePledge(entity, entity2);
        expect(pledgeService.comparePledge).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareAssociation', () => {
      it('Should forward to associationService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(associationService, 'compareAssociation');
        comp.compareAssociation(entity, entity2);
        expect(associationService.compareAssociation).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
