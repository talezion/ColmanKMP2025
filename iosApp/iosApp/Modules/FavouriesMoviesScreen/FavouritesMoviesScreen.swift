//
//  FavouritesMoviesScreen.swift
//  iosApp
//
//  Created by Tal Zion on 25/06/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct FavouritesMoviesScreen: View {
    
    @ObservedObject private(set) var viewModel = ViewModelWrapper<FavouritesMoviesState, FavouritesMoviesViewModel>()
    @State private var selectedMovie: Movie?
    
    var body: some View {
        NavigationStack {
            ZStack {
                switch onEnum(of: viewModel.uiState) {
                case .error(let error):
                    ErrorView(message: error.errorMessage)
                case .loading:
                    LoaderView()
                case .loaded(let movies):
                    MoviesView(movies: movies.movies) { movie in
                        selectedMovie = movie
                    }
                }
            }
            .navigationTitle("Favourites Movies") // Sets the title like a
            .onAppear {
                viewModel.startObserving()
            }
            .navigationDestination(item: $selectedMovie) { movie in
                MovieDetailView(movie: movie)
                    .navigationTitle(movie.title ?? "")
                    .toolbar {
                        Button("Bookmark") {
                            // save(movie)
                        }
                    }
                    
            }
        }
    }
}


#Preview {
    FavouritesMoviesScreen()
}
