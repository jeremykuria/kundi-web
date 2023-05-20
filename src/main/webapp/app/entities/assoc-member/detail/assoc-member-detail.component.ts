import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAssocMember } from '../assoc-member.model';

@Component({
  selector: 'jhi-assoc-member-detail',
  templateUrl: './assoc-member-detail.component.html',
})
export class AssocMemberDetailComponent implements OnInit {
  assocMember: IAssocMember | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ assocMember }) => {
      this.assocMember = assocMember;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
