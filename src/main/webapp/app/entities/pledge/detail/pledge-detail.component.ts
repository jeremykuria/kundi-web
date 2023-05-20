import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPledge } from '../pledge.model';

@Component({
  selector: 'jhi-pledge-detail',
  templateUrl: './pledge-detail.component.html',
})
export class PledgeDetailComponent implements OnInit {
  pledge: IPledge | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pledge }) => {
      this.pledge = pledge;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
