# HashTable class implements the hash table self-adjusting data structure. The structure and hash function 
# are inspired from instructor Cemal Tepe's course video lecture on Hash Tables.
from datetime import datetime, time
class HashTable:
    # Initialized to size 20 by default in order to utilize the hash function. Initialized
    # all rows to -1 to indicate empty
    def __init__(self, size = 20):
        self.table = []
        for i in range(size):
            #initial = [-1]
            self.table.append([-1])
        
    # Takes as input a package and uses the hash() method and modulo the length of the table (20)
    # to determine the appropriate row to append the package. This insert method performs an initial check to
    # determine if the row is empty and inserts the package at the first spot if empty. If not empty
    # the row will be ierated through and if the package has been previously added then it will be updated with the
    # new version of the package. If it is not already in the row, it will be appended at the end.
    def insert(self, package):
        row = hash(package.id) % len(self.table) 
        rowList = self.table[row]
        
        if rowList[0] == -1:
            rowList[0] = package
            return
        else:
            for p in rowList: 
                if p.id == package.id:
                    p = package
                    return
            rowList.append(package)

    # Takes in a package id and returns a string containing all of the package info
    def lookup(self, id):
        row = hash(id) % len(self.table) 
        rowList = self.table[row]
        result = "Package not found"
        if rowList[0] == -1:
            return result
        else:
            for p in rowList: 
                if p.id == id:
                    result = f"ID = {p.id}; Address =  {p.address}, {p.city}, {p.zip}; Weight = {p.weight}; Deadline = {p.deadline}; Status = {p.status} at {p.deliveryTime.time()}"
                    return result
        return result

    # Takes in a package id and a time and returns a string with the package info for the given time
    def lookup_time(self, packageId, atTime):
        row = hash(packageId) % len(self.table) 
        rowList = self.table[row]
        result = "Package not found"
        if rowList[0] == -1:
            return result
        else:
            for p in rowList: 
                if p.id == packageId:
                    end = ''    
                    status = 'at the hub'
                    if atTime >= p.departTime:
                        status = 'en route'
                    if atTime >= p.deliveryTime.time():
                        status = 'delivered'
                        end = 'at ' + str(p.deliveryTime.time()) 
                    result = f"At time {atTime}: ID = {p.id}; Address =  {p.address}, {p.city}, {p.zip}; Weight = {p.weight}; Deadline = {p.deadline}; Status = {status} {end}"
                    return result
        return result

    # Prints the package info for every package
    def printAll(self):
        for row in self.table:
            if row[0] == -1:
                continue
            else:
                for index in row:
                    print(f"ID = {index.id}; Address =  {index.address}, {index.city}, {index.zip}; Weight = {index.weight}; Deadline = {index.deadline}; Status = {index.status} at {index.deliveryTime.time()}")

    # Takes in a time and prints the package info for every package at the given time
    def printAllTime(self, atTime):
        for row in self.table:
            if row[0] == -1:
                continue
            else:
                for index in row:
                    end = ''    
                    status = 'at the hub'
                    if atTime >= index.departTime:
                        status = 'en route'
                    if atTime >= index.deliveryTime.time():
                        status = 'delivered'
                        end = 'at ' + str(index.deliveryTime.time())
                    print(f"At time {atTime}: ID = {index.id}; Address =  {index.address}, {index.city}, {index.zip}; Weight = {index.weight}; Deadline = {index.deadline}; Status = {status} {end}")