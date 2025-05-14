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
    
    @ObservedObject private(set) var viewModel = MoviesViewModelWrapper()
    
    var body: some View {
        
        NavigationView {
            VStack {
                switch onEnum(of: viewModel.uiState) {
                case .loading: LoadingView()
                case .loaded(let movies): MoviesView(movies: movies.movies)
                case .error(let error): ErrorView(message: error.errorMessage)
                }
            }
            .navigationTitle("Movies")
            .onAppear() {
                viewModel.startObserving()
            }
        }
    }
}

struct MoviesView: View {
    
    let movies: Movies
    
    var body: some View {
        
        List(movies.items, id: \.id) { movie in
            MovieRowView(movie: movie)
        }
        .listStyle(.plain)
    }
}

struct MovieRowView: View {
    
    let movie: Movie
    
    var body: some View {
        HStack(alignment: .top, spacing: 12) {
            MovieImageView(posterPath: movie.posterPath)
                .aspectRatio(2/3, contentMode: .fill)
                .frame(width: 100, height: 150)
                .clipped()
                .cornerRadius(8)
            
            VStack(alignment: .leading, spacing: 6) {
                Text(movie.title)
                    .font(.headline)
                    .foregroundColor(.primary)
                    .lineLimit(2)
                
                Text(movie.id)
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
            
            ZStack {
                Image(.dieHard)
                    .resizable()
                    .scaledToFill()
                    .frame(width: geometry.size.width, height: imageHeight)
                
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


extension MoviesScreen {
    
    @MainActor
    class MoviesViewModelWrapper: ObservableObject {
        
        let viewModel: MoviesViewModel
        @Published var uiState: MoviesState
        
        init() {
            self.viewModel = MoviesViewModel()
            self.uiState = viewModel.uiState.value
        }
        
        func startObserving() {
            Task {
                for await state in viewModel.uiState {
                    self.uiState = state
                }
            }
        }
    }
    
}



#Preview {
    MoviesScreen()
}
