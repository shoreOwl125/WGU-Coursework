# This program is designed for my performance assessment 
# for C950. It implements a package delivery system.
# Author: Andrew Dahlstrom
# SID: 001474509

from Package import Package
from HashTable import HashTable
from NearestNeighbor import NearestNeighbor
from datetime import datetime, time
import csv

# This method implements the menu for the text interface
def menuPrompt():
    print("\n Choose from the following options:")
    print("(1) Print all packages")
    print("(2) Lookup package info by ID")
    print("(3) Print delivery status of package at specified time")
    print("(4) Print delivery status of all packages at specified time")
    print("(5) Exit program")
    userInput = input("Enter corresponding (#): ")
    print(" ")
    return userInput
        
# main program implements text interface and runs delivery system program
def main():
    #Read package data from CSV file into truck packing lists
    truck1_list = []
    truck2a_list = []
    truck2b_list = []
    with open('WGUPS Package File CSV.csv', newline = '') as packageFile:
        reader = csv.reader(packageFile)
        next(reader, None) # Skip column titles
        for id, address, city, state, zip, deadline, weight, notes in reader:
            # Convert to appropriate data types
            id = int(id)
            if deadline == "EOD":
                deadline = datetime.strptime('23:59', '%H:%M').time()
            else:
                deadline = datetime.strptime(deadline, '%H:%M %p').time()
            weight = float(weight)
            # Create a new package object to store the data
            newPackage = Package(id, address, city, state, zip, deadline, weight, notes)
            # Packages have been arbitrarily assigned to each truck package list to fulfill special requirements
            if id in [25, 26, 31, 32, 6, 28, 7, 29, 1, 4, 40, 2, 33, 22]:
                truck1_list.append(newPackage)
            if id in [20, 21, 13, 39, 14, 15, 16, 34, 5, 9, 37, 38, 8, 30, 10, 19]:
                truck2a_list.append(newPackage)
            if id in [36, 3, 11, 35, 27, 12, 23, 24, 18, 17]:
                truck2b_list.append(newPackage)

    # Read address distances from CSV file and store them in a distance table
    distanceSearch = NearestNeighbor()
    with open('WGUPS Distance Table CSV.csv', newline = '') as distanceFile:
        reader = csv.reader(distanceFile)
        next(reader, None) # Skip column titles
        i = 0
        for row in reader:
            distanceSearch.indexToAddress[i] = row[1] # add label to address table
            distanceSearch.addressToIndex[row[1]] = i
            distanceSearch.distanceTable.append([])
            for column in row[2:]: # skip first two label columns
                distanceSearch.distanceTable[i].append(float(column))
            i += 1
   
    # Run Nearest Neighbor algorithm on each package list and retreive delivery data
    truck1_distance, truck1_time, truck1_updated_list = distanceSearch.deliver_packages(truck1_list, datetime.strptime('09:05', '%H:%M'))
    truck2a_distance, truck2a_time, truck2a_updated_list = distanceSearch.deliver_packages(truck2a_list, datetime.strptime('08:30', '%H:%M'))
    truck2b_distance, truck2b_time, truck2b_updated_list = distanceSearch.deliver_packages(truck2b_list, datetime.strptime('11:00', '%H:%M'))
    
    # Load updated package status into the self-adjusting hash table package status table
    package_status_table = HashTable()
    for p in truck1_updated_list:
        package_status_table.insert(p)
    for p in truck2a_updated_list:
        package_status_table.insert(p)
    for p in truck2b_updated_list:
        package_status_table.insert(p)

   
    # Display delivery truck reports 
    print("Truck Delivery Report:")
    print("Truck 1 finished deliveries at time ", truck1_time, " and traveled ", truck1_distance, " miles.")
    print("Truck 2 finished deliveries at time ", truck2a_time, " and traveled ", truck2a_distance, " miles.")
    print("Truck 2 finished deliveries at time ", truck2b_time, " and traveled ", truck2b_distance, " miles.")
    print("Total miles = ", truck1_distance + truck2a_distance + truck2b_distance)
    
    # Prompt menu options respond to user input until program is exited
    userInput = menuPrompt()
    while userInput:
        # Prints all package data
        if userInput == "1":
            package_status_table.printAll()
        # Prints package data for entered package ID
        elif userInput == "2":
            packageId = int(input("Enter package ID: "))
            print(package_status_table.lookup(packageId))
        # Prints package data for entered package ID and specified time
        elif userInput == "3":
            print("Print delivery status of package at specified time")
            packageId = int(input("Enter package ID: "))
            atTime = datetime.strptime(input("Enter a time in format HH:MM : "), '%H:%M').time()
            print(package_status_table.lookup_time(packageId, atTime))
        # Prints all package data for specified time
        elif userInput == "4":
            print("Print delivery status of all packages at specified time")
            atTime = datetime.strptime(input("Enter a time in format HH:MM : "), '%H:%M').time()
            package_status_table.printAllTime(atTime)
        # Exits program
        elif userInput == "5":
            print("Goodbye")
            quit()
        userInput = menuPrompt()    

# Initiate program
if __name__ == "__main__":
    main()

