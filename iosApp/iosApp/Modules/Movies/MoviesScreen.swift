//
//  MoviesScreen.swift
//  iosApp
//
//  Created by Tal Zion on 14/05/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct MoviesScreen: View {
    
    @State private var selectedMovie: Movie?
    @ObservedObject private(set) var viewModel = MoviesViewModelWrapper()
    
    var body: some View {
        
        NavigationStack {
            VStack {
                switch onEnum(of: viewModel.uiState) {
                case .loading:
                    LoadingView()
                case .loaded(let movies):
                    MoviesView(movies: movies.movies) { movie in
                        // TODO: 
                    }
                case .error(let error):
                    ErrorView(message: error.errorMessage)
                }
            }
            .navigationTitle("Movies")
            .onAppear() {
                viewModel.startObserving()
            }
            .navigationDestination(item: $selectedMovie) { movie in
                MovieDetailScreen(movie: movie)
            }
        }
    }
}


struct MoviesView: View {
    
    let movies: Movies
    let onMovieSelected: (Movie) -> Void
    
    var body: some View {
        
        List(movies.items, id: \.id) { movie in
            MovieRowView(movie: movie, onMovieSelected: onMovieSelected)
        }
        .listStyle(.plain)
    }
}

struct MovieRowView: View {
    
    let movie: Movie
    let onMovieSelected: (Movie) -> Void
    
    var body: some View {
        HStack(alignment: .top, spacing: 12) {
            MovieImageView(posterPath: movie.posterPath)
                .aspectRatio(2/3, contentMode: .fill)
                .frame(width: 100, height: 150)
                .clipped()
                .cornerRadius(8)
            
            VStack(alignment: .leading, spacing: 6) {
                Text(movie.title ?? "")
                    .font(.headline)
                    .foregroundColor(.primary)
                    .lineLimit(2)
                
                Text("\(movie.id ?? 1)")
                    .font(.subheadline)
                    .foregroundColor(.secondary)
                    .lineLimit(2)
            }
        }
        .padding(.vertical, 8)
        .padding(.horizontal, 12)
    
    }
}

struct MovieImageView: View {
    
    let posterPath: String?
    private let aspectRatio: CGFloat = 9 / 16
    
    var body: some View {
        GeometryReader { geometry in
            
            let imageHeight = geometry.size.height * aspectRatio
            let url = URL(string: "https://image.tmdb.org/t/p/w500/\(posterPath ?? "")")
            
            
            ZStack {
                AsyncImage(url: url) { phase in
                    
                    switch phase {
                    case .empty:
                        ProgressView()
                            .frame(maxWidth: .infinity, maxHeight: .infinity)
                    case .success(let image):
                        image
                            .resizable()
                            .scaledToFill()
                            .frame(width: geometry.size.width, height: imageHeight)
                    case .failure(let error):
                        // Default value
                        EmptyView()
                    @unknown default:
                        EmptyView()
                    }
                }
                
            }
        }
        
    }
}

struct LoadingView: View {
    
    var body: some View {
        ProgressView()
    }
}

struct ErrorView: View {
    
    var message: String
    
    var body: some View {
        Text(message)
            .font(.title)
    }
}



#Preview {
//    MoviesScreen()
}
