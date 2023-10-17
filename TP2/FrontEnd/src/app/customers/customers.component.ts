import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.css']
})
export class CustomersComponent implements OnInit {
  customers: any;

  constructor(private http:HttpClient) {}

  ngOnInit() {
    this.http.get("http://localhost:8083/CUSTOMER-SERVICE/customers").subscribe({
      next : data=>{
        this.customers=data
        console.log(data);
      },
      error : err => {

      }
    })
  }

    getBills(c: any) {

    }
}
