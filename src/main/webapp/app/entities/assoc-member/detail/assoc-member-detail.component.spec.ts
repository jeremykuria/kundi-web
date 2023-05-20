import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AssocMemberDetailComponent } from './assoc-member-detail.component';

describe('AssocMember Management Detail Component', () => {
  let comp: AssocMemberDetailComponent;
  let fixture: ComponentFixture<AssocMemberDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AssocMemberDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ assocMember: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AssocMemberDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AssocMemberDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load assocMember on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.assocMember).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
