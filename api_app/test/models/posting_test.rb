require 'test_helper'

class PostingTest < ActiveSupport::TestCase
  # test "the truth" do
  #   assert true
  # end

  test "order should be most recent first" do
    assert_equal postings(:most_recent), Posting.first
  end
end
