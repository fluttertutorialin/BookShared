//
// Created by Lakhani Kamlesh on 03/03/26.
//

import Foundation
import Shared

/*
class BooksObservable: ObservableObject {
    //1
    //let viewModel = ViewModelFactory().booksViewModel()

    //2
    let viewModel = ViewModelFactory().booksViewModel()

    @Published var books: [Book] = []

    //callback
    /* init() {
        viewModel.observeBooks() { books in
            self.books = books
        }
        viewModel.loadBook()
    } */

    init() {
        viewModel.observeFLow(
            flow: viewModel.book,
            onChange: { [weak self] value in
                if let list = value as? [Book] {
                    self?.books = list
                }
            }
        )
    }

    deinit {
        viewModel.clear()
    }
}*/


@Observable
class BooksObservable {
    let viewModel = ViewModelFactory().booksViewModel()

       var books: [Book] = []

       init() {
           viewModel.observeFLow(flow: viewModel.book) { [weak self] value in
                   self?.books = value as! [Book]
               }
       }

       deinit {
           viewModel.clear()
       }
}
