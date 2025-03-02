# Fetch Data Display Android App

## Overview
This Android application fetches data from an online JSON endpoint and displays it in a structured, easy-to-read format. The app demonstrates fundamental Android development concepts including:

- Network API requests using Volley
- JSON parsing
- RecyclerView implementation
- Data filtering and sorting
- Grouping and displaying organized content

## Requirements Implemented

The application successfully implements all the specified requirements:

1. **Data Retrieval**: Fetches data from the Fetch Hiring API endpoint
2. **Filtering**: Removes any items where the "name" field is null or empty
3. **Sorting**: Sorts items first by "listId" (ascending) and then by "name" 
4. **Display**: Presents the data in an organized list using RecyclerView

## Technical Implementation

### Components

- **MainActivity**: Handles data fetching, processing, and display setup
- **Item**: Model class representing each data item with id, listId, and name
- **Adapter**: Custom RecyclerView adapter to display items in the list
- **Layout Files**: Define the UI structure for the activity and list items

### Network Handling

The app uses Volley to make HTTP requests to the API endpoint and process the JSON response efficiently.

### Data Processing

For each item in the response:
1. The app checks if the "name" field is null or empty and filters out such items
2. Valid items are added to a collection
3. Items are then sorted by listId (primary) and name (secondary)
4. Numeric values in names (e.g., "Item 276") are extracted for natural sorting

### UI Implementation

The data is displayed in a RecyclerView with a clear layout that shows:
- Item ID
- List ID
- Item Name

## Challenges Overcome

- **Empty Data Handling**: Implemented robust null/empty checking for the name field
- **Natural Sorting**: Added logic to extract and compare numeric parts of item names for intuitive sorting
- **Error Handling**: Added comprehensive error handling for network requests and JSON parsing
- **User Feedback**: Added appropriate messages when no data is available or network errors occur

## Screenshots

[Screenshots would be included here in a real README]

## Future Improvements

- Add a refresh mechanism to reload data
- Implement visual grouping with headers for each listId
- Add loading indicators during data fetch
- Implement offline caching of previously fetched data
- Add unit and UI tests

## How to Run

1. Clone the repository
2. Open the project in Android Studio
3. Build and run on an emulator or physical device

## Dependencies

- Volley for network requests
- AndroidX libraries for UI components
- RecyclerView for list display
