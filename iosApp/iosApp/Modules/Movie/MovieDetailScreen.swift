//
//  MovieDetailScreen.swift
//  iosApp
//
//  Created by Tal Zion on 21/05/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct MovieDetailScreen: View {
    
    @ObservedObject private(set) var viewModel: MovieViewModelWrapper = MovieViewModelWrapper()
    let movie: Movie
    
    var body: some View {
        
        VStack {
            
        }
        .onAppear() {
            viewModel.startObserving()
        }
    }
}

#Preview {
//    MovieDetailScreen()
}
