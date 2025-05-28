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
    
    @ObservedObject private(set) var viewModel: MoviesViewModelWrapper = MoviesViewModelWrapper()
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
            .navigationTitle("Movies") // Sets the title like a
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

struct MovieDetailView: View {
    let movie: Movie
    
    var body: some View {
        Text("Details for \(movie.title ?? "")")
        
    }
}

struct MoviesListView: View {
    let movies: Movies
    
    var body: some View {
        List(movies.items, id: \.id) { movie in
            MovieRowView(movie: movie)
                .listRowInsets(EdgeInsets()) // Removes default padding
        }
        .listStyle(.plain)
    }
}

struct MovieRowView: View {
    let movie: Movie
    
    var body: some View {
        HStack(alignment: .top, spacing: 12) {
            MovieImageView(pathUrl: movie.posterPath ?? "")
                .aspectRatio(2/3, contentMode: .fill)
                .frame(width: 100, height: 150)
                .clipped()
                .cornerRadius(8)
            
            VStack(alignment: .leading, spacing: 6) {
                Text(movie.title ?? "")
                    .font(.headline)
                    .foregroundColor(.primary)
                    .lineLimit(2)
                
                Text(movie.title ?? "")
                    .font(.subheadline)
                    .foregroundColor(.secondary)
                    .lineLimit(2)
            }
            
            Spacer()
        }
        .padding(.vertical, 8)
        .padding(.horizontal, 16)
    }
}

struct MoviesView: View {
    
    let movies: Movies
    let onMovieSelected: (Movie) -> Void
    
    private let columns = [
        GridItem(.flexible(), spacing: 0),
        GridItem(.flexible(), spacing: 0)
    ]
    
    var body: some View {
        ScrollView {
            LazyVGrid(columns: columns, spacing: 0) {
                ForEach(movies.items, id: \.id) { movie in
                    Button(action: {
                        onMovieSelected(movie)
                    }) {
                        MovieView(movie: movie)
                    }
                    .buttonStyle(.plain)
                }
            }
            .padding(.horizontal, 0)
        }
    }
}

struct MovieView: View {
    var movie: Movie
    
    var body: some View {
        ZStack(alignment: .bottomLeading) {
            MovieImageView(pathUrl: movie.posterPath ?? "")
            
            VStack(alignment: .leading, spacing: 4) {
                Text(movie.title ?? "")
                    .font(.headline)
                    .foregroundColor(.white)
                    .lineLimit(2)
                    .padding(.horizontal, 4)
                    .padding(.bottom, 4)
            }
            .frame(maxWidth: .infinity, alignment: .leading)
        }
        .padding(8)
    }
}

struct LoaderView: View {
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

struct MovieImageView: View {
    
    let pathUrl: String
    private let aspectRatio: CGFloat = 9/16
    
    var body: some View {
        GeometryReader { geometry in
            let imageHeight = geometry.size.width / aspectRatio
            let gradientHeight = imageHeight * 0.3 // Dynamic 30% height
            
            ZStack(alignment: .bottomLeading) {
                AsyncImage(url: URL(string: "https://image.tmdb.org/t/p/w500\(pathUrl)")) { phase in
                    switch phase {
                    case .empty:
                        ProgressView()
                            .frame(maxWidth: .infinity, maxHeight: .infinity)
                    case .success(let image):
                        image
                            .resizable()
                            .scaledToFill()
                            .frame(width: geometry.size.width, height: imageHeight)
                            .clipShape(RoundedRectangle(cornerRadius: 5))
                    case .failure:
                        Image(systemName: "exclamationmark.triangle.fill")
                            .resizable()
                            .scaledToFit()
                            .foregroundColor(.red)
                            .frame(width: geometry.size.width, height: imageHeight)
                    @unknown default:
                        EmptyView()
                    }
                }
                
                // Gradient Overlay
                LinearGradient(
                    gradient: Gradient(colors: [Color.black.opacity(0.8), Color.black.opacity(0.3), Color.clear]),
                    startPoint: .bottom,
                    endPoint: .top
                )
                .frame(height: gradientHeight)
                .frame(width: geometry.size.width, alignment: .bottom)
                
            }
            .clipShape(RoundedRectangle(cornerRadius: 10))
        }
        .aspectRatio(9/16, contentMode: .fit) // Ensures correct scaling
    }
}


//    WebImage(url: URL(string: "https://image.tmdb.org/t/p/original/\(pathUrl)"))

#Preview {
    MoviesScreen()
}
