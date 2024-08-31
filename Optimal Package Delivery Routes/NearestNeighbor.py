from datetime import datetime, timedelta, time
from Package import Package

class NearestNeighbor:
    # Constructor for a new graph object. Member variables are distanceTable which stores the distances between locations 
    # and two lookup tables to convert between address string and index
    def __init__(self):
        self.distanceTable = [] # List of lists containing all distances between locations
        self.indexToAddress = {} # key = index and value = address 
        self.addressToIndex = {} # key = address and value = index
        
    # Function used to compute the delivery order and time needed to deliver each package.
    # Uses Nearest Neighbor algorithim structure partially inspired from Wikipedia entry
    # https://en.wikipedia.org/wiki/Nearest_neighbour_algorithm
    # Takes as input a package file and a departing time. Returns the total distance traveled including return trip, 
    # the time arriving back at HUB and the updated package list.
    def deliver_packages(self, package_list, depart_time):
        current_location = 'HUB'
        total_distance = 0
        updated_Package_List = []
        visited_list = []
        package_list_size = len(package_list)
        
        # For handling the special case of package #9. 
        # Needs to be removed until correct address is provided.
        special_case = False
        special_package = None
        for p in package_list:
            if p.id == 9:
                special_case = True
                special_package = p
                package_list.remove(p)
        
        # Continue until all the packages have been added to the delivered list
        while len(visited_list) < package_list_size:

            # Special case for package #9 add it in at end
            if special_case and len(visited_list) == package_list_size - 1:
                package_list.append(special_package)

            #Find packages with earliest deadlines
            deadline = datetime.strptime('23:59', '%H:%M').time()
            for p in package_list:
                if p.id not in visited_list:
                    if p.deadline < deadline:
                        deadline = p.deadline

            # Add all packages with same deadline to unvisited queue
            unvisited_queue = []
            for p in package_list:
                if p.id not in visited_list:
                    if p.deadline <= deadline:
                        unvisited_queue.append(p)

            # Search for nearest neighbor (location)
            local_distance = float('inf')
            nn = 'HUB'
            for p in unvisited_queue:
                if self.distanceTable[self.addressToIndex[current_location]][self.addressToIndex[p.address]] < local_distance:
                    nn = p.address
                    local_distance = self.distanceTable[self.addressToIndex[current_location]][self.addressToIndex[p.address]]
            
            current_location = nn # Update current location
            total_distance += local_distance #update total distance
            current_time = depart_time + timedelta(minutes = ((total_distance * 60)/18)) # Calculate current time
            
            # Search for all packages with same address and add to the updated package list
            for p in package_list:
                if p.id not in visited_list:
                    if p.address == current_location:
                        updatedPackage = Package(p.id, p.address, p.city, p.state, p.zip, p.deadline, p.weight, p.notes)
                        updatedPackage.set_depart_time(depart_time.time())
                        updatedPackage.set_delivery_time(current_time)
                        updatedPackage.set_status('delivered')
                        updated_Package_List.append(updatedPackage)
                        visited_list.append(p.id)

        # Return the total distance traveled including return trip, the time arriving back at HUB and the updated package list.
        return_distance = self.distanceTable[0][self.addressToIndex[current_location]]
        total_distance += return_distance
        current_time = depart_time + timedelta(minutes = ((total_distance * 60)/18))
        return(float(f'{total_distance:.1f}'), current_time.time(), updated_Package_List)

