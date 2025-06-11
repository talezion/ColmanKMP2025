//
//  MovieViewModelWrapper.swift
//  iosApp
//
//  Created by Tal Zion on 11/06/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import Shared

extension MovieDetailScreen {
    
    @MainActor
    class MovieViewModelWrapper: ObservableObject {
        
        let viewModel: MovieViewModel
        @Published var uiState: MovieState
        
        init() {
            self.viewModel = KoinKt.movieViewModel()
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
