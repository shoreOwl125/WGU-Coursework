#Package class implements package data structures

from datetime import time

class Package:
    # Constructor for package object. The status, departTime and deliveryTime are set as default
    # values until updated with setters
    def __init__(self, id, address, city, state, zip, deadline, weight, notes):
        self.id = id
        self.address = address
        self.city = city
        self.state = state
        self.zip = zip
        self.deadline = deadline
        self.weight = weight
        self.notes = notes
        self.status = "at the hub"
        self.departTime = time(8, 0, 0)
        self.deliveryTime = time(8, 0, 0)

    # Setter method to update the package status
    def set_status(self, status):
        self.status = status

    # Setter method to update the package status
    def set_delivery_time(self, time):
        self.deliveryTime = time  

    # Setter method to update the package status
    def set_depart_time(self, time):
         self.departTime = time 


    